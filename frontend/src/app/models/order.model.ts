
export enum OrderStatus {
    Pending = 'PENDING',
    Shipped = 'SHIPPED',
    Delivered = 'DELIVERED',
    Cancelled = 'CANCELLED'
  }

  export interface OrderItem{
    productName : string,
    unitPrice : number,
    price : number,
    quantity : number,
    categoryName : string;
  }

  export interface OrderItemRequest{
    productId:number,
    quantity : number
  }

export interface OrderRequest{
  customerName : string;
  customerEmail : string;
  orderItems : OrderItemRequest[]
}


export interface Order{
    orderId : number;
    customerName : string;
    customerEmail: string;
    date : Date;
    totalPrice : number;
    orderItems : OrderItem[];
    orderStatus: OrderStatus;
}