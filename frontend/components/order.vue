<template>
  <div>
    <form @submit="submitOrder" method="post">
      <input
        class="input is-rounded"
        v-bind:class="{ 'is-danger': this.storeOrderError }"
        type="text"
        v-model="orderString"
        placeholder="Items to purchase"
        id="order-input"
      />
      <input type="submit" value="Submit" style="display: none" />
    </form>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'order',
  data() {
    return {
      orderString: undefined,
      order: undefined,
      orderError: false,
    }
  },
  computed: {
    ...mapState({
      storeOrder: (state) => state.order,
      storeOrderError: (state) => state.orderError,
    }),
  },
  methods: {
    async submitOrder(e) {
      e.preventDefault()
      await this.$store.dispatch('fetchOrder', this.orderString)
      await this.$store.dispatch('fetchOrderSum', this.orderString)
    },
  },
}
</script>

<style></style>
