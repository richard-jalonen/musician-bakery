<template>
  <div class="container">
    <div v-if="storeOrder">Total: ${{ this.storeOrderSum }}</div>
    <div v-if="this.storeOrderSum">
      <form @submit="postOrderPayment" method="post">
        <input
          class="input is-rounded"
          v-bind:class="{ 'is-danger': this.storePaymentError }"
          type="text"
          v-model="paymentString"
          placeholder="Amount paid"
          id="payment-input"
        />
        <input type="submit" value="Submit" style="display: none" />
      </form>
    </div>

    <div v-if="this.storeChange">Change: ${{ this.storeChange }}</div>
    <button v-if="this.storeChange" @click="reloadStore" id="reset-page">
      Reset
    </button>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'payment',
  data() {
    return {
      paymentString: undefined,
    }
  },
  computed: {
    ...mapState({
      storeOrder: (state) => state.order,
      storeOrderSum: (state) => state.orderSum,
      storePaymentError: (state) => state.paymentError,
      storeChange: (state) => state.change,
    }),
  },
  methods: {
    async postOrderPayment(e) {
      e.preventDefault()
      await this.$store.dispatch('postOrderPayment', this.paymentString)
    },
    reloadStore() {
      location.reload()
    },
  },
}
</script>

<style></style>
