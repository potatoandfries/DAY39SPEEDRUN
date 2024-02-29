import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-gif-search',
  templateUrl: './gif-search.component.html',
  styleUrl: './gif-search.component.css'
})
export class GifSearchComponent implements OnInit {

  private fb: FormBuilder = inject(FormBuilder)
  private router: Router = inject(Router)
  form!: FormGroup
  
  ngOnInit(): void {
    this.form = this.createForm()
  }

  createForm(): FormGroup {
    return this.fb.group({
      query: this.fb.control<string>('', [Validators.required])
    })
  }

  process(){
    this.router.navigate(['/search'], { queryParams: { query: this.form.value['query']}})
  }
}
