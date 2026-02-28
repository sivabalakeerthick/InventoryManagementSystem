export interface Product {
    productId?: number;
    productName: string;
    unitPrice: number;
    categoryName: string;
    description: string;
    reOrderLevel: number,
    active?:boolean;
  }


