<template>
  <el-select v-bind="$attrs" v-on="$listeners" no-data-text="没有可用的优惠券" placeholder="请选择优惠券">
    <el-option v-for="(item, index) in subjects" :label="item.name" :key="index" :value="item.price"/>
  </el-select>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'coupon-select',
  props: {
    courseId: {
      type: [String, Number],
      required: true
    }
  },
  computed: {
    ...mapGetters('field', { subjects: 'coupons' })
  },
  created () {
    this.getCouponList(this.courseId)
  },
  methods: {
    ...mapActions('field', ['getCouponList'])
  }
}
</script>
