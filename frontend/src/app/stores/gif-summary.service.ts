import { Injectable } from '@angular/core';
import { Summary, SummarySlice } from '../models';
import { ComponentStore } from '@ngrx/component-store';

const INIT_STATE = {
  loadedOn: 0,
  summaries: []
}
@Injectable({
  providedIn: 'root'
})
export class GifSummaryService extends ComponentStore<SummarySlice>{

  constructor() { super(INIT_STATE) }

  
  readonly loadToStore = this.updater<Summary[]>(
    (slice: SummarySlice, values: Summary[]) => {
      return {
        loadedOn: (new Date()).getTime(),
        summaries: values
      } as SummarySlice
    }
  )

  readonly getAllSummaries = this.select<Summary[]>(
    (slice: SummarySlice) => {
      return slice.summaries
    }
  )
}