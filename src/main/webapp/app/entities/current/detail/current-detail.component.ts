import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICurrent } from '../current.model';

@Component({
  selector: 'jhi-current-detail',
  templateUrl: './current-detail.component.html',
})
export class CurrentDetailComponent implements OnInit {
  current: ICurrent | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ current }) => {
      this.current = current;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
