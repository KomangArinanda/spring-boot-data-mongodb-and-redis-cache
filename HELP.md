# Getting Started

### Entity:

#### Product -> collection products

* list colors
* date createdDate
* string id
* date lastModifiedDate
* string name
* double price
* int stock
* long version

#### Order -> collection orders

* date date
* string id
* list productDetails
* double totalPayment
* long version

#### ProductOrderDetail

* string color
* string id
* string name
* double price
* int quantity

### API Specification:

#### Product Controller

* GET /products?page=0,size=10
* POST /products
* PUT /products/{productId}
* DELETE /products/{productId}

#### Order Controller

* POST /orders

#### Report Controller

* GET /reports

MongoDB Aggregation to generate the report:

https://docs.mongodb.com/manual/reference/operator/aggregation/unwind/
> db.getCollection('orders').aggregate([{$unwind:"$productDetails"}])

https://docs.mongodb.com/manual/reference/operator/aggregation/group/
https://docs.mongodb.com/manual/reference/operator/aggregation/first/
https://docs.mongodb.com/manual/reference/operator/aggregation/sum/
> db.getCollection('orders').aggregate([
{
    $unwind:"$productDetails"
},
{
    $group:{
        _id:"$productDetails._id",
        name: {$first:"$productDetails.name"},
        id: {$first:"$productDetails._id"},
        sales: { $sum: { $multiply: [ "$productDetails.price", "$productDetails.quantity" ] } },
        quantity: { $sum : "$productDetails.quantity"}
    }
}
])

https://docs.mongodb.com/manual/reference/operator/aggregation/sort/
> db.getCollection('orders').aggregate([
{
    $unwind:"$productDetails"
},
{
    $group:{
        _id:"$productDetails._id",
        name: {$first:"$productDetails.name"},
        id: {$first:"$productDetails._id"},
        sales: { $sum: { $multiply: [ "$productDetails.price", "$productDetails.quantity" ] } },
        quantity: { $sum : "$productDetails.quantity"}
    }
},
{
    $sort : { 
        sales : -1
    }
}
])
