import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Gif, Summary } from '../models';
import { environment as env } from '../../environments/environment'
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GifService {

  private client: HttpClient = inject(HttpClient)

  searchGif(query: string): Promise<Summary[]> {
    let params: HttpParams = new HttpParams()
                            .set("query", query)
    return firstValueFrom(this.client.get<Summary[]>(env.search_url, {params: params}))
  }

  getGif(gid: string): Promise<Gif> {
    let url = `${env.get_url}/${gid}`
    return firstValueFrom(this.client.get<Gif>(url))
  }

  createPost(gid: string, comment: string): Promise<any> {
    return firstValueFrom(this.client.post(`${env.post_url}/${gid}/comment`, {comment: comment}))
  }
}
