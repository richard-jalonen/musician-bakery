export const state = () => ({
  order: undefined,
  orderSum: undefined,
  change: undefined,
  orderError: false,
  paymentError: false,
})

export const mutations = {
  setOrder(state, payload) {
    state.order = payload
  },
  setOrderError(state, payload) {
    state.orderError = payload
  },
  setOrderSum(state, payload) {
    state.orderSum = payload
  },
  setPayment(state, payload) {
    state.change = payload
  },
  setPaymentError(state, payload) {
    state.paymentError = payload
  },
}

export const actions = {
  async fetchOrder({ commit }, orderString) {
    commit('setOrderError', false)
    const orderData = await this.$axios
      .get('http://localhost:8080/order/' + orderString)
      .then((response) => {
        return response.data
      })
      .catch((err) => {
        if (err.response) {
          console.log(err)
          commit('setOrderError', true)
        }
      })
    commit('setOrder', orderData)
  },
  async fetchOrderSum({ commit, state }) {
    const sumData = await this.$axios
      .post('http://localhost:8080/payment/checkout-amount', state.order)
      .then((response) => {
        return response.data
      })
      .catch((err) => {
        if (err.response) {
          console.log(err)
        }
      })
    commit('setOrderSum', sumData)
  },
  async postOrderPayment({ commit, state }, payload) {
    commit('setPaymentError', false)
    const paymentSum = await this.$axios
      .post(
        'http://localhost:8080/payment/pay?checkoutAmount=' +
          state.orderSum +
          '&amountPaid=' +
          payload,
        state.order
      )
      .then((response) => {
        return response.data
      })
      .catch((err) => {
        if (err.response) {
          console.log(err)
          commit('setPaymentError', true)
        }
      })
    commit('setPayment', paymentSum)
  },
}
