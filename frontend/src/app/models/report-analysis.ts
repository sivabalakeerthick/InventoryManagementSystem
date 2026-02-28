export interface ReportAndAnalysisDTO {
    startDate: string; 
    endDate: string; 
  }
  
  export interface ProductQuantityResponseDTO {
    productName: string;
    totalQuantity: number;
  }
  
  export interface TopSupplierResponseDTO {
    supplierName: string;
    totalPurchaseAmount: number;
  }  
  export interface Order {
    orderId: number;
    totalAmount: number;
    orderStatus: string;
    orderedDate: string;
  }
  export interface PurchaseOrder {
    purchaseOrderId: number;
    totalAmount: number;
    orderStatus: string; 
    purchasedDate: string;
  }