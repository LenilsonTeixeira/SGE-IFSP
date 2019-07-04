import { Component, OnInit, ContentChild, Input } from '@angular/core';
import { FormControlName } from '@angular/forms';
import { UppercaseDirective } from '../uppercase.directive';

@Component({
  selector: 'sge-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.css']
})
export class InputComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  ngAfterContentInit() {
    this.input = this.control;
    if(this.input === undefined){
      throw new Error('Esse componente precisa ser usado com uma diretiva FormControlName');
    }
  }

  input: any;

  @Input() errorMessage: string;

  @Input() label: string;

  @ContentChild(FormControlName) control :FormControlName; 

  hasSuccess(): boolean{
    return this.input.valid && (this.input.dirty || this.input.touched);
  }

  hasError(): boolean{
    return this.input.invalid && (this.input.dirty || this.input.touched);
  }

}
