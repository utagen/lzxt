<template>
  <div class="learn-list-item">
    <div class="status-icon"><i :class="statusIconClasse"/></div>
    <div class="subject-name">{{item.subjectName}}</div>
    <div class="cell cell-1">
      <div><label>状态：</label><span :class="orderStatusClasses">{{orderStatusText}}</span></div>
      <div><label>单元：</label><span>{{unitText}}</span></div>
    </div>
    <div class="cell cell-2">
      <div><label>开始时间：</label><span>{{item.createTime}}</span></div>
      <div><label>结束时间：</label><span>{{item.finishTime | formatText}}</span></div>
    </div>
    <div class="cell cell-3">
      <div><span :class="completedStatusClasses">{{completedStatusText}}</span></div>
      <div><label>用时：</label><span>{{item.useTime | formatText}}</span></div>
    </div>
    <div class="cell cell-4">
      <template v-if="orderStatusValid">
          <el-button v-if="isNotCompleted" type="primary" @click="gotoLearn">继续学习</el-button>
          <el-button v-else type="primary" @click="reviewPracticle" class="increase-width">查看</el-button>
      </template>
    </div>
  </div>
</template>

<script>
export default {
  name: 'my-learn-list-item',
  props: {
    item: {
      type: Object,
      required: true
    }
  },
  filters: {
    formatText (value) {
      return value || ''
    }
  },
  computed: {
    orderStatusValid () {
      return this.item.status === 0
    },
    unitText () {
      return this.item.subjectUnitList.join('，')
    },
    orderStatusText () {
      return this.orderStatusValid ? '有效' : '失效，已到期'
    },
    orderStatusClasses () {
      return ['status', { valid: this.orderStatusValid, invalid: !this.orderStatusValid }]
    },
    statusIconClasse () {
      const status = ['pending', 'completed', 'canceled']
      const { historyStatus } = this.item
      return ['lzxt-icon', `lzxt-icon-${status[historyStatus - 1]}`]
    },
    isNotCompleted () {
      const { historyStatus } = this.item
      return historyStatus === 1
    },
    completedStatusText () {
      const status = ['未完成', '已完成', '已取消']
      const { historyStatus } = this.item
      return status[historyStatus - 1]
    },
    completedStatusClasses () {
      const { historyStatus } = this.item
      return ['status', {
        completed: historyStatus === 2,
        pending: historyStatus === 1,
        canceled: historyStatus === 3
      }]
    }
  },
  methods: {
    gotoLearn () {
      const { subjectId, subjectUnitList } = this.item
      const params = this.$encode({ id: subjectId, units: subjectUnitList })
      this.$router.push(`/courses/${params}/learning`)
    },
    reviewPracticle () {
      const params = this.$encode({ id: this.item.id })
      this.$router.push(`/practice/${params}/review`)
    }
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/vars.scss';
@import '~@/styles/mixins.scss';

.learn-list-item {
  position: relative;
  background-color: $--color-white;
  border-radius: $--border-radius-base;
  border: $--border-base;
  margin-bottom: 20px;
  padding: 60px 20px 20px;

  &:hover {
    box-shadow: $--box-shadow-base;
  }

  .subject-name {
    @include ellipsis();

    position: absolute;
    padding: 0 10px;
    text-align: center;
    width: 220px;
    color: $--color-white;
    background-color: $--color-primary-light-1;
    height: 40px;
    line-height: 40px;
    border-bottom-left-radius: $--border-radius-base;
    border-bottom-right-radius: $--border-radius-base;
    top: 0;
    left: 20px;
  }

  .cell {
    display: inline-block;
    line-height: 30px;
    width: 28%;

    &:last-child {
      width: 16%;
    }

    .status {
      &.valid, &.completed {
        color: $--color-success;
      }

      &.invalid, &.pending {
        color: $--color-danger;
      }

      &.canceled {
        color: $--color-text-secondary;
      }
    }

    .el-button{
      width: 80px;
    }
  }

  .status-icon{
    position: absolute;
    top: 20px;
    right: 20px;
    font-size: 60px;

    .lzxt-icon-canceled {
      color: $--color-info;
    }

    .lzxt-icon-completed {
      color: $--color-success;
    }

    .lzxt-icon-pending {
      color: $--color-danger;
    }
  }
}
</style>

<style lang="scss" scoped>
@media screen and (max-width: 900px) {
  .learn-list-item {
    .cell-2 {
      width: 42%;
    }

    .cell-3 {
      width: 14%;
    }

    .cell-4 {
      width: 14%;
    }
  }
}
@media screen and (max-width: 580px) {
   .learn-list-item {
     .status-icon {
       top: 0;
       right: 0;
       z-index: 10;
     }
      .cell {
        display: block;
        width: 100%;
      }
   }
}

@media screen and (max-width: 320px) {
  .learn-list-item .subject-name {
    left: 50%;
    max-width: 90%;
    transform: translateX(-50%);
  }
}
</style>
