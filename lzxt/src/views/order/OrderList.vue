<template>
  <section class="order-list-wrap">
    <div class="breadcrumb-wrap exercise">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item to="/">首页</el-breadcrumb-item>
        <el-breadcrumb-item>订单管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <empty-list v-if="isEmpty">
      <span>知识就是力量，赶快去买点课程来学一学吧！</span>
    </empty-list>
    <template v-else>
      <div class="grid-wrap">
        <order-list-item
          v-for="item in list" :item="item"
          :key="item.id"
          @on-delete="handleDelete"
          @on-pay="handleOnPay">
        </order-list-item>
      </div>
      <div class="footer-wrap" v-if="showFooter">
        <el-pagination
          layout="total, prev, pager, next, jumper"
          hide-on-single-page
          :current-page.sync="page"
          :page-size="pageSize"
          :total="total"
          @current-change="switchPagination">
        </el-pagination>
      </div>
    </template>
  </section>
</template>
<script>
import { mapState, mapActions } from 'vuex'
import OrderListItem from './OrderListItem.vue'

export default {
  name: 'order-list',
  components: { OrderListItem },
  computed: {
    ...mapState('order', {
      list: state => state.orderList.list,
      total: state => state.orderList.total
    }),
    isEmpty () {
      return this.total === 0
    },
    showFooter () {
      return this.total > this.pageSize
    }
  },
  data () {
    return { page: 1, pageSize: 10 }
  },
  mounted () {
    this.getOrderList({ page: this.page, pageSize: this.pageSize })
  },
  methods: {
    ...mapActions('order', ['getOrderList', 'deleteOrder']),
    switchPagination (page) {
      this.getOrderList({ page, pageSize: this.pageSize }).then(() => {
        this.$scrollTo()
      })
    },
    handleDelete ({ orderId }) {
      this.$confirm('您确定要删除订单吗？', '确认', {
        center: true,
        customClass: 'confirm-dialog'
      }).then(() => {
        this.deleteOrder(orderId).then(() => {
          this.page = 1
          this.$notify({ message: '订单已删除!', type: 'success', position: 'bottom-right' })
          this.switchPagination(1)
        })
      })
    },
    handleOnPay (item) {
      const { course, orderId } = item
      const subjectName = course.subjectList.map(item => item.subjectName).join(',')
      const params = this.$encode({
        orderId: orderId,
        courseName: course.courseName,
        subject: subjectName,
        amount: course.courseZhePrice
      })
      this.$router.push(`/pay/order/${params}`)
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
