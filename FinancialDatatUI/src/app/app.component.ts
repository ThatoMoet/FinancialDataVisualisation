import {Component, ElementRef, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);
@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  standalone: false,
  styleUrl: 'app.component.css'
})
export class AppComponent {
  @ViewChild('myChart', {static: false}) myChart!:ElementRef;

  apiURL = 'http://localhost:8080/api'
  userId: number = 1;
  year: number= 2025;
  fileToUpload: File | null = null;
  records: any[]=[];
  userName = '';
  chart:any;
  successMessage: string = '';



  constructor(private http:HttpClient) {
  }
  onFileToUpload(event:any){
    this.fileToUpload=event.target.files[0];
  }

  uploadFile(){
    if (!this.userId || this.userId <= 0) {
      this.successMessage = 'Error: Please enter a valid User ID';
      return;
    }
    const currentYear = new Date().getFullYear();
    if (!this.year || this.year < 2000 || this.year > currentYear + 10) {
      this.successMessage = `Error: Year must be between 2000 and ${currentYear + 10}`;
      return;
    }



    if (!this.fileToUpload) return;
    const formData = new FormData();
    formData.append('excelFile',this.fileToUpload);

    this.http.post(`${this.apiURL}/finances/upload/${this.userId}/${this.year}`,
      formData,
      {
        responseType:'text'
      }).subscribe({next: (response) =>{
        this.successMessage=response;
        this.fetchRecords()
      },
      error: (err) => console.error(err)

    });

  }
  fetchRecords(){
    this.http.get<any[]>(`${this.apiURL}/finances/${this.userId}/${this.year}`)
      .subscribe({
        next: (data) =>{
          this.records = data;
          if (data.length > 0){
            this.userName= data[0].user.name;
            this.createChart(data);
          }
        },
        error:(err)=>console.error(err)
      });
  }
  createChart(data: any[]){
    if(this.chart){
      this.chart.destroy();
    }
    const ctx = this.myChart.nativeElement.getContext('2d');
    this.chart = new Chart(ctx,{
      type:'bar',
      data:{
        labels: data.map(r=> r.month),
        datasets:[{
          label: 'Amount (R)',
          data:data.map(r=>r.amount),
          backgroundColor: 'rgba(75.192,192,8.6)'
        }

        ]
      }
    })
  }
}
