export interface Stock{
    productId : number,
    name : string,
    quantity : number,
    status : StockStatus,
    reOrderLevel : number,
    lastUpdated : Date
}


export type StockStatus = "IN_STOCK" | "OUT_OF_STOCK" | "LOW_STOCK" | "NOT_AVAILABLE";
