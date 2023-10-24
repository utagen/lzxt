<template>
  <section>
    <div class="breadcrumb-wrap exercise">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item to="/">首页</el-breadcrumb-item>
        <el-breadcrumb-item to="/orders">订单管理</el-breadcrumb-item>
        <el-breadcrumb-item>创建订单</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <named-header title="创建订单"></named-header>
    <div class="form-wrap">
      <el-form label-position="left" label-width="120px" >
        <el-form-item label="商品名称:">
          <label class="text-primary" slot="label">学科名称:</label>
          <span class="text-primary">{{course.courseName}}</span>
        </el-form-item>
        <el-form-item label="包含学科:">{{course.subjectInfo}}</el-form-item>
        <el-form-item label="售价:">¥{{course.price}}</el-form-item>
        <el-form-item label="优惠（优惠券）:">
          <coupon-select :courseId="courseId" v-model="couponId" />
        </el-form-item>
        <el-form-item label="订单金额:">¥{{course.price}}</el-form-item>
        <el-form-item label-width="0">
          <div class="button-group">
            <el-button @click="$router.go(-1)">返回</el-button>
            <el-button type="primary" @click="handleSubmit">提交订单</el-button>
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
    product: {
      type: String,
      required: true
    }
  },
  computed: {
    ...mapState('course', ['course'])
  },
  data () {
    const { courseId } = this.$decode(this.product)
    return {
      courseId: courseId,
      couponId: ''
    }
  },
  mounted () {
    this.getCourse(this.courseId)
  },
  methods: {
    ...mapActions('course', ['getCourse']),
    ...mapActions('order', ['createOrder']),
    handleSubmit () {
      const { courseId, couponId } = this.$data
      this.createOrder({ courseId, couponId }).then(data => {
        const params = this.$encode(data)
        this.$router.push(`/pay/order/${params}`)
      })
    }
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/vars.scss';

.text-primary {
  color: $--color-primary;
}

.button-group {
  padding: 30px;

  .el-button {
    margin-left: 120px;
    min-width: 100px;

    &:first-child {
      margin-left: 30px;
    }
  }
}
</style>

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
