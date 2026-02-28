import { Component, Input, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent implements OnChanges {
  @Input() totalItems: number = 0;
  @Input() itemsPerPage: number = 5;
  @Input() currentPage: number = 1;
  @Input() maxVisiblePages: number = 5;

  @Output() pageChange = new EventEmitter<number>();

  totalPages: number = 0;
  pages: number[] = [];

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['totalItems'] || changes['itemsPerPage'] || changes['currentPage'] || changes['maxVisiblePages']) {
      this.calculatePagination();
    }
  }

  private calculatePagination(): void {
    this.totalPages = Math.ceil(this.totalItems / this.itemsPerPage);
    this.pages = [];

    let startPage = Math.max(1, this.currentPage - Math.floor(this.maxVisiblePages / 2));
    let endPage = Math.min(this.totalPages, startPage + this.maxVisiblePages - 1);

    if (endPage - startPage + 1 < this.maxVisiblePages) {
      startPage = Math.max(1, endPage - this.maxVisiblePages + 1);
    }

    for (let i = startPage; i <= endPage; i++) {
      this.pages.push(i);
    }
  }

  goToPage(page: number): void {
    if (page >= 1 && page <= this.totalPages && page !== this.currentPage) {
      this.pageChange.emit(page - 1);
    }
  }

  goToPrevPage(): void {
    if (this.currentPage > 1) {
      this.pageChange.emit(this.currentPage - 2);
    }
  }

  goToNextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.pageChange.emit(this.currentPage);
    }
  }
}