import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { GifService } from '../../services/gif.service';
import { Gif } from '../../models';

@Component({
  selector: 'app-gif',
  templateUrl: './gif.component.html',
  styleUrl: './gif.component.css'
})
export class GifComponent implements OnInit {
  private activateRoute: ActivatedRoute = inject(ActivatedRoute)
  private gifSvc: GifService = inject(GifService)

  gif$!: Promise<Gif>

  ngOnInit(): void {
    let gid: string = this.activateRoute.snapshot.params['gid']
    this.gif$ = this.gifSvc.getGif(gid)
  }
}
