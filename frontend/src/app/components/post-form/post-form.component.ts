import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { GifService } from '../../services/gif.service';

@Component({
  selector: 'app-post-form',
  templateUrl: './post-form.component.html',
  styleUrl: './post-form.component.css'
})
export class PostFormComponent implements OnInit{

  private fb: FormBuilder = inject(FormBuilder)
  private activatedRoute: ActivatedRoute = inject(ActivatedRoute)
  private router: Router = inject(Router)
  private gifSvc: GifService = inject(GifService)

  form!: FormGroup
  gid!: string
  title!: string

  ngOnInit(): void {
    this.form = this.createForm()
    this.gid = this.activatedRoute.snapshot.params['gid']
    this.gifSvc.getGif(this.gid)
    .then((gif)=>{
      this.title = gif.title;
    })
  }

  createForm(): FormGroup {
    return this.fb.group({
      comment: this.fb.control<string>('', [Validators.required])
    })
  }

  process(): void {
    this.gifSvc.createPost(this.gid, this.form.value['comment'])
    .then(() => {
      this.router.navigate(['/gif', this.gid])
    })
    .catch((error) => {
      console.log(error)
    })
  }
}
