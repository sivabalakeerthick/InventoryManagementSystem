export interface Supplier {
  supplierId?: number;
  name: string;
  contact: string;
}
export interface Page<T> {
  content: T[];        
  number: number;            
  size: number;             
  totalElements: number;     
  totalPages: number;        
  first: boolean;            
  last: boolean;             
  empty: boolean;
  numberOfElements: number;  

  pageable: {
    pageNumber: number;
    pageSize: number;
    offset: number;
    paged: boolean;
    unpaged: boolean;
    sort: {
      empty: boolean;
      sorted: boolean;
      unsorted: boolean;
    };
  };
  sort: {
    empty: boolean;
    sorted: boolean;
    unsorted: boolean;
  };
}