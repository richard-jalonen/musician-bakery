export const state = () => ({
  order: undefined,
  orderSum: undefined,
  change: undefined,
  orderError: undefined,
  paymentError: undefined,
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
    commit('setOrderError', undefined)
    const orderData = await this.$axios
      .get('http://localhost:8080/order/' + orderString)
      .then((response) => {
        return response.data
      })
      .catch((err) => {
        if (err.response) {
          if (err.response.data) commit('setOrderError', err.response.data)
          else commit('setOrderError', err)
        }
      })
    commit('setOrder', orderData)
  },
  async fetchOrderSum({ commit, state }) {
    if (!state.order) return
    const sumData = await this.$axios
      .post('http://localhost:8080/payment/checkout-amount', state.order)
      .then((response) => {
        return response.data
      })
      .catch((err) => {
        if (err.response) {
          if (err.response.data) commit('setOrderError', err.response.data)
          else commit('setOrderError', err)
        }
      })
    commit('setOrderSum', sumData)
  },
  async postOrderPayment({ commit, state }, payload) {
    commit('setPaymentError', undefined)
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
          if (err.response.status == 409)
            commit('setPaymentError', err.response.data)
          else commit('setPaymentError', err)
        }
      })
    commit('setPayment', paymentSum)
  },
}
