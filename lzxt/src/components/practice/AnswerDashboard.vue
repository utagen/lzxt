<template>
  <div v-if="topicType === 3" :class="rootClasses">
    <slot :slot-scope="$props">
      <h4 class="reference-title">参考答案：</h4>
      <div class="reference-answer" v-html="rightAnswer"></div>
    </slot>
  </div>
  <div v-else-if="topicType === 0" :class="rootClasses">
    <slot :slot-scope="$props">
      <span v-if="judge" :class="status |textClasses">
        <label>结果：</label><span>{{ status |statusText}}</span>
      </span>
      <span v-else></span>
      <span v-if="wrongTime">
        <label>错误时间：</label><span>{{wrongTime }}</span>
      </span>
      <span v-if="wrongCounts">
        <label>错误次数：</label><span>{{wrongCounts }}</span>
      </span>
    </slot>
  </div>
  <div v-else :class="rootClasses">
    <slot :slot-scope="$props">
      <span :class="status | textClasses">
        <label>你的答案：</label><span>{{userAnswer}}</span>
      </span>
      <span v-if="judge" :class="status |textClasses">
        <label>结果：</label><span>{{ status |statusText}}</span>
      </span>
      <span :class="{'text-success': !judge}">
        <label>正确答案：</label><span>{{rightAnswer }}</span>
      </span>
      <span v-if="wrongTime">
        <label>错误时间：</label><span>{{wrongTime }}</span>
      </span>
      <span v-if="wrongCounts">
        <label>错误次数：</label><span>{{wrongCounts }}</span>
      </span>
    </slot>
  </div>
</template>

<script>
export default {
  name: 'answer-dashboard',
  props: {
    userAnswer: {
      type: [String, Array],
      required: true
    },
    rightAnswer: {
      type: [String, Array],
      required: true
    },
    topicType: {
      type: Number,
      required: true
    },
    align: Boolean,
    judge: {
      type: Boolean,
      default: true
    },
    wrongTime: String,
    wrongCounts: {
      type: [String, Number],
      default: ''
    },
    status: {
      type: Number,
      required: true
    }
  },
  filters: {
    textClasses (status) {
      return status === 2 ? 'text-success' : 'text-danger'
    },
    statusText (status) {
      return status === 2 ? '正确' : '错误'
    }
  },
  computed: {
    rootClasses () {
      return ['answer-dashboard', {
        'space-between': this.align,
        'span-count-4': [1, 2, 4].indexOf(this.topicType) !== -1,
        'span-count-3': this.topicType === 0
      }]
    }
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/vars.scss';

.answer-dashboard {
  >span {
    margin-left: 40px;
    &:first-child { margin-left: 0;}
  }

  label {
    margin-right: 5px;
  }

  .text-success { color: $--color-success;}
  .text-danger { color: $--color-danger;}
  .reference-title {
    margin-bottom: 10px;
    font-weight: bold;
    font-size: $--font-size-large;
  }

  .reference-answer {
    line-height: 1.5;
  }

  &.space-between {
    >span {
      display: inline-block;
      margin-left: 0;
      width: 20%;
    }

    &.span-count-4 {
      >span:nth-child(3) {
        width: 40%;
      }
    }
    &.span-count-3 {
      >span:first-child {
        width: 40%;
      }
      >span:nth-child(2) {
        width: 40%;
      }
    }
  }

}
</style>

<style lang="scss" scoped>
 @media screen and (max-width: 660px) and (min-width: 560px ) {
   .answer-dashboard {
     &.space-between > span {
       line-height: 30px;
       width: 50% !important;
     }
   }
 }

  @media screen and (max-width: 560px ) {
    .answer-dashboard.space-between>span {display: block; width: 100% !important; line-height: 25px;}
  }
</style>
