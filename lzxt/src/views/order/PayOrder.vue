<template>
  <section class="form-container">
    <div class="breadcrumb-wrap exercise">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item to="/">首页</el-breadcrumb-item>
        <el-breadcrumb-item to="/orders">订单管理</el-breadcrumb-item>
        <el-breadcrumb-item>支付订单</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <named-header title="支付订单"></named-header>
    <div class="form-wrap">
      <el-form label-position="left" label-width="120px" >
        <el-form-item label="商品名称:">
          <label class="text-primary" slot="label">商品名称:</label>
          <span class="text-primary">{{courseName}}</span>
        </el-form-item>
        <el-form-item label="包含科目:">{{subject}}</el-form-item>
        <el-form-item label="订单金额:">¥{{amount}}</el-form-item>
        <el-form-item label="支付方式:" class="el-form-item-pay" v-if="!$weixin">
          <wechat-qrcode v-if="webchatPayUrl" :url="webchatPayUrl" :size="120" @loaded="monitorOrderStatus"/>
        </el-form-item>
        <el-form-item label-width="0">
          <div class="button-group">
            <el-button @click="$router.push('/orders')">取消</el-button>
            <el-button type="primary" v-if="$weixin" @click="weixinpay">立即支付</el-button>
            <el-button type="primary" v-else @click="$router.push('/orders')">确认支付</el-button>
          </div>
        </el-form-item>
      </el-form>
    </div>
  </section>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'create-order',
  props: {
    order: {
      type: String,
      required: true
    }
  },
  beforeRouteEnter (to, from, next) {
    if (['create-order', 'order-list'].indexOf(from.name) === -1) {
      next('/orders')
    } else {
      next()
    }
  },
  computed: {
    ...mapState('order', ['webchatPayUrl'])
  },
  data () {
    const { amount, courseName, orderId, subject } = this.$decode(this.order)
    return { amount, courseName, orderId, subject, timer: 0 }
  },
  async mounted () {
    if (this.$weixin) {
      await this.weixinpay()
    } else {
      await this.getWebchatPayUrl(this.orderId)
    }
  },
  methods: {
    ...mapActions('order', ['getWebchatPayUrl', 'getOrderById', 'getWeixinPay']),
    monitorOrderStatus () {
      this.pollingStatus()
    },
    pollingStatus () {
      this.timer = setTimeout(() => {
        this.getOrderById(this.orderId).then(data => {
          if (data.payStatus === 2) {
            this.$router.push('/orders')
          } else {
            this.pollingStatus()
          }
        })
      }, 1000)
    },
    weixinpay () {
      return this.getWeixinPay(this.orderId).then(data => {
        this.$weixinPay(data).then(() => {
          this.$router.push('/orders')
        })
      })
    }
  },
  beforeRouteLeave (to, from, next) {
    clearInterval(this.timer)
    next()
  },
  beforeDestroy () {
    clearInterval(this.timer)
  }
}
</script>

<style lang="scss" scoped>
@media screen and (max-width: 550px) {
  .button-group {
    padding: 30px 0;
    .el-button {
      margin-left: 30px
    }

  }
}

@media screen and (max-width: 340px) {
  .button-group .el-button {
    margin-left: 20px;

    &:first-child {
      margin-left: 0;
    }
  }
}
</style>

<style lang="scss">
@media screen and (max-width: 400px) {
  .el-form-item.el-form-item-pay {
    .el-form-item__content {
      margin-top: 32px;
      margin-left: 0!important;
      text-align: center;
    }
  }
}
</style>
