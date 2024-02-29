import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GifSearchComponent } from './components/gif-search/gif-search.component';
import { GifListComponent } from './components/gif-list/gif-list.component';
import { GifComponent } from './components/gif/gif.component';
import { PostFormComponent } from './components/post-form/post-form.component';

const routes: Routes = [
  { path: '', component: GifSearchComponent},
  { path: 'search', component: GifListComponent },
  { path: 'gif/:gid', component: GifComponent },
  { path: 'gif/:gid/comment', component: PostFormComponent },
  { path: '**', redirectTo: '/', pathMatch: 'full'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
