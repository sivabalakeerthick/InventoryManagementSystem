export interface PurchaseOrderItem {
  id?: number; 
  productId: number;
  productName?: string; 
  quantity: number;
  amount?: number;
}
export interface PurchaseOrder {
  purchaseOrderId?: number;
  supplierId: number;
  supplierName?: string;
  purchaseOrderItemList: PurchaseOrderItem[];
  paymentType: string;
  orderStatus?: string;
  totalAmount?: number;
  purchaseDate?: string;
}
export interface PurchaseOrderUpdate {
  orderStatus?: string;
  paymentType?: string; 
}
export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}