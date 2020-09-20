<template>
  <div class="container mt-3 mb-5">
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
      <div
        v-if="this.storePaymentError"
        class="notification is-danger is-light mt-3"
      >
        {{ this.storePaymentError }}
      </div>
    </div>
    <div class="mt-3">
      <div
        v-if="this.storeOrder"
        class="has-text-right has-text-weight-medium is-family-monospace"
      >
        Total: ${{ this.storeOrderSum }}
      </div>
      <div
        v-if="this.storeChange"
        class="has-text-right has-text-weight-medium is-family-monospace"
      >
        Change: ${{ this.storeChange }}
      </div>
    </div>
    <button
      v-if="this.storeChange"
      @click="reloadStore"
      id="reset-page"
      class="button is-rounded mt-3"
    >
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
      this.paymentString = undefined
    },
    reloadStore() {
      location.reload()
    },
  },
}
</script>

<style></style>
