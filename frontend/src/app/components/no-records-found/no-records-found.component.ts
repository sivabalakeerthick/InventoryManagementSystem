import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-no-records-found',
  templateUrl: './no-records-found.component.html',
  styleUrls: ['./no-records-found.component.css']
})
export class NoRecordsFoundComponent {
  @Input() totalElements: number = 0; 
  @Input() message: string = 'No records found.'; 
}