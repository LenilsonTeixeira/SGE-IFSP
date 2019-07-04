import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'sge-content-header',
  templateUrl: './content-header.component.html',
  styleUrls: ['./content-header.component.css']
})
export class ContentHeaderComponent implements OnInit {

  constructor() { }

  @Input() nome: string;

  @Input() param1: string;

  @Input() param2: string;

  ngOnInit() {
  }

}
