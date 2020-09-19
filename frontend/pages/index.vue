<template>
  <div class="container">
    <div>
      <form id="app" @submit="submitOrder" method="post">
        <input
          class="input is-rounded"
          v-bind:class="{ 'is-danger': orderError }"
          type="text"
          v-model="orderString"
          placeholder="Items to purchase"
        />
        <input type="submit" value="Submit" style="display: none" />
      </form>
      {{ this.order }}
      <br />
      <div v-if="orderSum">Total: ${{ this.orderSum }}</div>
      <div v-if="orderSum">
        <form id="app" @submit="payForOrder" method="post">
          <input
            class="input is-rounded"
            v-bind:class="{ 'is-danger': paymentError }"
            type="text"
            v-model="paymentString"
            placeholder="Amount paid"
          />
          <input type="submit" value="Submit" style="display: none" />
        </form>
      </div>

      <div v-if="change">Change: ${{ this.change }}</div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      order: undefined,
      orderString: undefined,
      orderSum: undefined,
      paymentString: undefined,
      change: undefined,
      orderError: false,
      paymentError: false,
    }
  },
  mounted() {},
  methods: {
    submitOrder: async function (e) {
      this.orderError = false
      e.preventDefault()
      this.order = await this.$axios
        .get('http://localhost:8080/order/' + this.orderString)
        .then((response) => {
          return response.data
        })
        .catch((err) => {
          if (err.response) {
            console.log(err)
            this.orderError = true
          }
        })
      this.getOrderSum()
    },
    getOrderSum: async function () {
      this.orderSum = await this.$axios
        .post('http://localhost:8080/payment/checkout-amount', this.order)
        .then((response) => {
          return response.data
        })
        .then((this.orderString = undefined))
    },
    payForOrder: async function (e) {
      this.paymentError = false
      e.preventDefault()
      this.change = await this.$axios
        .post(
          'http://localhost:8080/payment/pay?checkoutAmount=' +
            this.orderSum +
            '&amountPaid=' +
            this.paymentString,
          this.order
        )
        .then((response) => {
          return response.data
        })
        .then((this.paymentString = undefined))
        .catch((err) => {
          if (err.response) {
            console.log(err)
            this.paymentError = true
          }
        })
    },
  },
}
</script>

<style>
.container {
  margin: 1em auto;
  min-height: 97vh;
  display: flex;
  justify-content: center;
  text-align: center;
}
</style>
