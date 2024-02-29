import { Component, OnInit, inject } from '@angular/core';
import { Summary } from '../../models';
import { GifService } from '../../services/gif.service';
import { ActivatedRoute } from '@angular/router';
import { GifSummaryService } from '../../stores/gif-summary.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-gif-list',
  templateUrl: './gif-list.component.html',
  styleUrl: './gif-list.component.css'
})
export class GifListComponent implements OnInit {

  private gifSvc: GifService = inject(GifService)
  private activatedRoute: ActivatedRoute = inject(ActivatedRoute)
  private gifStore: GifSummaryService = inject(GifSummaryService)

  // gifList$!: Promise<Summary[]>
  gifList$!: Observable<Summary[]>
  gifList!: Summary[]
  minPage: number = 1
  maxPage!: number
  state!: number
  next: boolean = false
  previous: boolean = true

  ngOnInit(): void {
    let q: string = this.activatedRoute.snapshot.queryParams['query']
    // this.gifList$ = this.gifSvc.searchGif(q)
    this.gifSvc.searchGif(q)
    .then((array) => {
      this.gifList = array
      if (array.length % 5 != 0){
         this.maxPage = Math.floor(array.length / 5) + 1
      }
      this.maxPage = array.length / 5
      this.state = 1
      this.gifList$ = this.gifStore.getAllSummaries
      this.gifStore.loadToStore(this.gifList.slice((this.state-1)*5, this.state*5))
    })
  }

  nextPage(): void {
    this.state += 1
    if (this.state != this.minPage){
      this.previous = false
    }

    if (this.state == this.maxPage){
      this.next = true;
    }
    this.gifStore.loadToStore(this.gifList.slice((this.state-1)*5, this.state*5))
  }
  
  previousPage(): void {
    this.state -= 1
    if (this.state != this.maxPage){
      this.next = false
    }
    if (this.state == this.minPage){
      this.previous = true;
    }
    this.gifStore.loadToStore(this.gifList.slice((this.state-1)*5, this.state*5))
  }
}
