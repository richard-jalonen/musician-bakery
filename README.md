# musician-bakery

# ABOUT

Full stack technical assignment for a developer position.

# RUNNING IN LOCALHOST

## Backend

From the 'backend' folder run (using cmd):

> gradlew bootrun

## Frontend

From the 'frontend' folder run (using cmd):

> npm i

> npm run dev

# BACKEND ENDPOINTS

- > GET /order/{orderRequest}  
  > input: PathVariable comma delimited String with product abbreviations, valid example - "B,B,C,C,M,W,C,W,B"  
  > returns: Order object (Map<String, Long>) - String is the product abbreviation from user input and Long the amount ordered.

- > GET /payment/checkout-amount  
  > input: RequestBody Order object (Map<String, Long>) - example:  
  > {  
  > &nbsp;&nbsp;"order": {  
  > &nbsp;&nbsp;&nbsp;&nbsp;"B": 1,  
  > &nbsp;&nbsp;&nbsp;&nbsp;"C": 1,  
  > &nbsp;&nbsp;&nbsp;&nbsp;"W": 1,  
  > &nbsp;&nbsp;&nbsp;&nbsp;"M": 1  
  > &nbsp;&nbsp;}  
  > }  
  > returns: BigDecimal - order total sum.

- > POST /payment/pay  
  > inputs:  
  > RequestParam BigDecimal checkoutAmount - order total sum  
  > RequestParam BigDecimal amountPaid - amount of "cash" paid by the client  
  > RequestBody Order order - order to pay for - same format as in GET /order/{orderRequest}  
  > returns: BigDecimal - change amount to give back to the customer.

# SETUP TODO

- ~~spring boot backend setup~~
- ~~demo endpoint/health check~~
- ~~db (h2?) setup~~
- ~~react/vue? frontend setup~~
- ~~axios -> backend demo query~~
- ~~simple architercture plan~~
- ~~localhost guide in readme~~
