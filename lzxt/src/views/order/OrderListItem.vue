<template>
  <div class="order-list-item">
    <div class="product-name"><label>课程名称：</label><span>{{item.course.courseName}}</span></div>
    <div class="cell cell-1">
      <div><label>订单编号：</label><span>{{item.orderId}}</span></div>
      <div><label>包含学科：</label><span>{{subjectName}}</span></div>
      <div><label>创建时间：</label><span>{{item.createTime}}</span></div>
    </div>
    <div class="cell cell-2">
      <div><label>售价：</label><span>¥{{item.course.coursePrice}}</span></div>
      <div>&nbsp;</div>
      <div><label>优惠：</label><span>¥{{item.couponAmount}}</span></div>
    </div>
    <div class="cell cell-3">
      <div><label>订单金额：</label><span>{{item.oamount}}</span></div>
      <div><label>订单状态：</label><span>{{orderStatusText}}</span></div>
      <div><label>支付方式：</label><span>{{payTypeText}}</span></div>
    </div>
    <div class="cell cell-4">
      <div class="buttom-item"><el-button v-if="pendingOrder" type="primary" @click="$emit('on-pay', item)">去支付</el-button></div>
      <div class="buttom-item"><el-button type="info" v-if="!completedOrder" @click="$emit('on-delete', item)">删除</el-button></div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'order-list-item',
  props: {
    item: {
      type: Object,
      required: true
    }
  },
  computed: {
    pendingOrder () {
      return this.item.orderStatus <= 1
    },
    completedOrder () {
      return this.item.orderStatus === 2
    },
    canceledOrder () {
      return this.item.orderStatus === 3
    },
    subjectName () {
      const { subjectList } = this.item.course
      return subjectList.map(item => `${item.subjectName} ${item.subjectGrade}（${item.subjectTerm}）`).join(',')
    },
    orderStatusText () {
      const status = ['未支付', '已支付', '已取消']
      return status[Math.max(this.item.orderStatus - 1, 0)]
    },
    payTypeText () {
      const types = ['微信支付', '支付宝支付']
      return types[this.item.payType - 1]
    }
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/vars.scss';

.order-list-item {
  padding: 20px 40px;
  border-top: $--border-base;

  &:first-child { border-top: none;}

  .cell {
    vertical-align: top;
    display: inline-block;
    line-height: 25px;

    &-1 {
      width: 45%;
    }

    &-2 {
      width: 20%;
    }

    &-3 {
      width: 20%;
    }

    &-4 {
      width: 15%;

      .buttom-item {
        height: 40px;
      }

      .el-button {
        min-width: 70px;
      }
    }
  }
}

.product-name {
  color: $--color-primary;
  margin-bottom: 20px;
}
</style>

<style lang="scss" scoped>
@media screen and (max-width: 800px) {
  .order-list-item .cell{
    width: 100%;

    &-4 {
      display: flex;

      .buttom-item { flex: 1;  text-align:  center;}
    }
  }
}
</style>
