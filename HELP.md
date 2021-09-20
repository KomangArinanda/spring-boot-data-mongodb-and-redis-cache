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

