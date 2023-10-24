<template>
  <div class="practice-question">
    <div class="topic-title">{{index}}. {{item.topicTitle}}</div>
    <ul v-if="existToicImage" class="topic-images">
      <li v-for="(imageUrl, index) in item.topicImgList" class="topic-images-item" :key="index">
        <el-image :src="imageUrl" fit="contain">
          <div slot="error" class="image-slot"><i class="el-icon-picture-outline"></i></div>
        </el-image>
        <div class="topic-images-item__title">图&nbsp;&nbsp;{{ index + 1}}</div>
      </li>
    </ul>

    <!-- 填空题 -->
    <div v-if="isFillBlank" class="answer-wrap fill-bank-answer-wrap">
      <ol>
        <li v-for="(option, index) in blankOptions" :key="index">
          <label class="label-index">{{index + 1}}、</label>
          <template v-if="disabled">
            <span :class="option.rightAnswer | itemBlankClasses(blankAnswer[`item_${index}`])">{{blankAnswer[`item_${index}`]}}</span>
            <span class="correct-answer" v-if="option.rightAnswer !== blankAnswer[`item_${index}`]">{{option.rightAnswer}}</span>
          </template>
          <el-input v-else @change="hanleBlankChanged" v-model.trim="blankAnswer[`item_${index}`]" class="el-input__blank" />
        </li>
      </ol>
    </div>
    <!-- 单选题 -->
    <div class="answer-wrap single-choose-answer-wrap" v-else-if="isSingleChoose" >
      <el-radio-group v-model="answer" :disabled="disabled">
        <el-radio v-for="option in radioOptions" :label="option.name" size="medium" :class="item.answer| optionRadioClasses(option.name, userAnswer)" :key="option.name">
          <div class="option-title">{{option.name}}. {{option.content}}</div>
          <el-image v-for="(imageUrl, index) in option.imageList" :src="imageUrl" fit="contain" :key="index">
            <div slot="error" class="image-slot"><i class="el-icon-picture-outline"></i></div>
          </el-image>
        </el-radio>
      </el-radio-group>
    </div>
    <!-- 多选题 -->
    <div class="answer-wrap multi-choose-answer-wrap" v-else-if="isMultiChoose" >
      <el-checkbox-group v-model="checkboxAnswer" size="medium"  :disabled="disabled" @change="handleChangedChange">
        <el-checkbox v-for="option in checkboxOptions" :class="item.answer | optionCheckboxClasses(option.name, userAnswer)" :label="option.name" :key="option.name">
          <div class="option-title">{{option.name}}. {{option.content}}</div>
          <el-image v-for="(imageUrl, index) in option.imageList" :src="imageUrl" fit="contain" :key="index">
            <div slot="error" class="image-slot"><i class="el-icon-picture-outline"></i></div>
          </el-image>
        </el-checkbox>
      </el-checkbox-group>
    </div>
    <!-- 判断题 -->
    <div class="answer-wrap true-or-false-answer-wrap" v-else-if="isTrueOrFalse">
      <el-radio-group v-model="answer" size="medium" :disabled="disabled">
        <el-radio label="0" :class ="item.answer| optionRadioClasses('0', userAnswer)">对</el-radio>
        <el-radio label="1" :class ="item.answer| optionRadioClasses('1', userAnswer)">错</el-radio>
      </el-radio-group>
    </div>
    <!-- 问答题 -->
    <div class="answer-wrap question-and-answer-wrap" v-else-if="isQuestionAndAnswer">
      <div v-if="disabled">
        <h4 class="my-answer-title">我的答案：</h4>
        <div class="my-answer-content" v-html="userAnswerText"></div>
      </div>
      <el-input v-else type="textarea" :rows="8" v-model="answer"></el-input>
    </div>
    <slot
      :topicId="item.id"
      :userAnswer="answer"
      :view="view"
      :userAnswerText="userAnswerText"
      :topicType="item.topicType"
      :rightAnswer="item.answer"
      :rightAnswerText="rightAnswerText"
      :index="index"/>
  </div>
</template>

<script>
const reAssembleChooseAnswer = (list = [], rightAnswer, userAnswer) => {
  return list.map(item => {
    const options = Object.keys(item).map(option => ({
      name: option,
      content: item[option].content,
      imageList: item[option].imageList
    }))
    return options[0]
  })
}

const reAssembleBlankAnswer = (rightAnswer = [], userAnswer = '') => {
  const userAnswerItems = userAnswer.split(BLANK_SPLIT_CHAR)
  return rightAnswer.map((item, index) => ({
    name: item.id,
    rightAnswer: item.content,
    userAnswer: userAnswerItems[index] || ''
  }))
}

const BLANK_SPLIT_CHAR = '$#$'

export default {
  name: 'practice-question',
  props: {
    item: {
      type: Object,
      required: true
    },
    index: {
      type: Number,
      required: true
    },
    userAnswer: String,
    view: Boolean
  },
  filters: {
    optionCheckboxClasses: function (correct, item, choosed) {
      if (choosed) {
        const correctItems = correct.split(',')
        const choosedItems = choosed.split(',')
        const itemIncorrect = correctItems.indexOf(item) !== -1
        const itemInChoosed = choosedItems.indexOf(item) !== -1
        return {
          'standard-answer': itemIncorrect,
          correct: itemIncorrect && itemInChoosed,
          incorrect: !itemIncorrect && itemInChoosed
        }
      }
      return {}
    },
    optionRadioClasses (correct, item, choosed) {
      if (choosed) {
        const isCorrectAnswer = correct === item
        const isChoosedAnswer = choosed === item
        return {
          'standard-answer': isCorrectAnswer,
          correct: isCorrectAnswer && isChoosedAnswer,
          incorrect: !isCorrectAnswer && isChoosedAnswer
        }
      }
      return {}
    },
    itemBlankClasses (correct, filled) {
      const isCorrect = correct === filled
      return ['blank', {
        'correct-answer': isCorrect,
        'wrong-answer': !isCorrect
      }]
    }
  },
  computed: {
    existToicImage () {
      const { topicImgList } = this.item
      return topicImgList && topicImgList.length !== 0
    },
    disabled () {
      return this.view || !!(this.userAnswer && this.userAnswer !== '')
    },
    isFillBlank () {
      return this.item.topicType === 0
    },
    isSingleChoose () {
      return this.item.topicType === 1
    },
    isMultiChoose () {
      return this.item.topicType === 2
    },
    isQuestionAndAnswer () {
      return this.item.topicType === 3
    },
    isTrueOrFalse () {
      return this.item.topicType === 4
    },
    blankOptions () {
      const { fillBlankAnswer } = this.item
      return this.isFillBlank && reAssembleBlankAnswer(fillBlankAnswer, this.userAnswer || '')
    },
    radioOptions () {
      const { radioChoice, answer } = this.item
      return this.isSingleChoose && reAssembleChooseAnswer(radioChoice, answer, this.userAnswer)
    },
    checkboxOptions () {
      const { mulChoice, answer } = this.item
      return this.isMultiChoose && reAssembleChooseAnswer(mulChoice, answer, this.userAnswer)
    },
    userAnswerText () {
      if (this.isTrueOrFalse) {
        return this.answer === '0' ? '对' : '错'
      } else if (this.isFillBlank) {
        return this.answer ? this.answer.split(BLANK_SPLIT_CHAR) : []
      } else if (this.isQuestionAndAnswer) {
        return this.answer ? this.answer.replace(/\n|\r\n/g, '<br/>') : ''
      }
      return this.answer
    },
    rightAnswerText () {
      if (this.isTrueOrFalse) {
        return this.item.answer === '0' ? '对' : '错'
      } else if (this.isFillBlank) {
        return this.item.fillBlankAnswer.map(item => item.content)
      } else if (this.isQuestionAndAnswer) {
        return this.item.answer.replace(/\n|\r\n/g, '<br/>')
      }
      return this.item.answer
    }
  },
  data () {
    const blankAnswer = {}
    if (this.item.topicType === 0) {
      for (let i = 0; i < this.item.fillBlankTopicChoice; i++) {
        blankAnswer[`item_${i}`] = ''
      }
    }

    return {
      answer: this.userAnswer,
      checkboxAnswer: [],
      blankAnswer: blankAnswer
    }
  },
  methods: {
    handleChangedChange (checked) {
      this.answer = checked.sort(this.$compareFn).join(',')
    },
    hanleBlankChanged () {
      const arr = []
      for (let i = 0; i < this.item.fillBlankTopicChoice; i++) {
        arr.push(this.blankAnswer[`item_${i}`])
      }
      this.answer = arr.join(BLANK_SPLIT_CHAR)
    },
    initUserAnswer (newVal, oldVal) {
      if (newVal === oldVal) return
      if (this.item.topicType === 2) {
        if (!newVal) {
          this.checkboxAnswer = []
        } else {
          this.checkboxAnswer = newVal.split(',')
        }
      } else if (this.item.topicType === 0) {
        if (newVal) {
          newVal.split(BLANK_SPLIT_CHAR).forEach((item, index) => {
            this.$set(this.blankAnswer, `item_${index}`, item)
          })
        }
      }
      this.answer = newVal
    }
  },
  watch: {
    userAnswer: {
      handler: 'initUserAnswer',
      immediate: true
    }
  },
  beforeDestroy () {
    this.checkboxAnswer = []
    this.blankAnswer = {}
  }
}
</script>

<style lang="scss">
@import '~@/styles/vars.scss';

.practice-question {
  padding: 20px 40px;
  border-top: $--border-base;

  .topic-title {
    font-size: $--font-size-medium;
    padding: 10px 0;
    line-height: 2;
  }

  .topic-images {
    padding: 10px 0;
    text-align: center;

    .topic-images-item {
      display: inline-block;
      vertical-align: middle;
      margin: 5px;
    }

    .topic-images-item__title {
      padding: 10px 0 10px;
      text-align: center;
    }

  }

  .answer-wrap {
    .el-checkbox-group, .el-radio-group {
      display: block
    }

    .el-radio, .el-checkbox {
      display: block;
      white-space: normal;
      padding: 15px 0;
    }

    .el-radio {
      .el-radio__label {
          line-height: 1.4;
          font-size: $--font-size-medium;
          color: $--color-text-primary;
        }

        .el-radio__input.is-disabled {

          .el-radio__inner {
            border-color: $--border-color-base;
            background-color: $--color-white;
          }
        }

      &.incorrect {
        .el-radio__label {
          color: $--color-danger;
        }
        .el-radio__input.is-disabled {
          .el-radio__inner {
            border-color: $--color-danger;
            background-color: $--color-danger;
          }
          &.is-checked .el-radio__inner:after {
            background-color: $--color-white;
          }
        }
      }

      &.standard-answer {
        .el-radio__label {
          color: $--color-success;
        }

        .el-radio__input.is-disabled {

          .el-radio__inner {
            border-color: $--color-success;
            background-color: $--color-success;

            &:after {
              transform: translate(-50%, -50%) scale(1);
              background-color: $--color-white;
            }
          }
        }
      }
    }

    .el-checkbox {
      .el-checkbox__label {
          font-size: $--font-size-medium;
          line-height: 1.4;
          color: $--color-text-primary;
        }

        .el-checkbox__input {
          &.is-checked {
            .el-checkbox__inner:after {
              border-width: 2px;
            }
          }
          &.is-disabled {
            .el-checkbox__inner {
              border-color: $--border-color-base;
              background-color: $--color-white;
            }
          }
        }

      &.incorrect {
        .el-checkbox__input.is-disabled {
          .el-checkbox__inner {
            background-color: $--color-danger;
            border-color: $--color-danger;
            &:after {
              border-width: 2px;
              border-color: $--color-white;
            }
          }
        }
      }

      &.standard-answer {
        .el-checkbox__input.is-disabled {
          .el-checkbox__inner {
            background-color: $--color-success;
            border-color: $--color-success;
            &:after {
              border-width: 2px;
              border-color: $--color-white;
            }
          }
        }
      }
    }

    .el-radio__input, .el-checkbox__input {
      float: left;
      vertical-align: top;
      margin-right: 20px;
    }

    .el-radio__inner, .el-checkbox__inner {
      width: 18px;
      height: 18px;
    }
    .el-checkbox__inner::after {
      top: 3px;
      left: 6px
    }

    .el-radio__label, .el-checkbox__label {
      margin-left: 30px;
      display: block;
    }

    .el-input__blank {
      vertical-align: top;
      display: inline-block;
      width: 30%;

      .el-input__inner {
        font-size: 14px;
        border-top: none;
        border-left: none;
        border-right: none;
        border-radius: 0;
        border-bottom-color: $--color-text-primary;
      }
    }
  }
  .fill-bank-answer-wrap {
    margin-bottom: 40px;
    font-size: $--font-size-medium;

    .correct-answer {
      vertical-align: top;
      display: inline-block;
      line-height: 32px;
      color: $--color-success;
    }
    .wrong-answer {
      vertical-align: top;
      display: inline-block;
      line-height: 32px;
      color: $--color-danger;
      text-decoration: line-through;
      margin-right: 20px;
    }

    li {
      margin-top: 20px;

      .label-index {
        vertical-align: top;
        display: inline-block;
        margin-right: 10px;
        height: 32px;
        overflow: hidden;
        line-height: 32px;
      }
    }
  }

  .question-and-answer-wrap {
    font-size: $--font-size-medium;
    margin: 30px 0;
  }

  .my-answer-title {
    color: $--color-text-secondary;
    margin-bottom: 10px;
    font-size: $--font-size-large;
    font-weight: bold;
  }

  .my-answer-content {
    line-height: 1.5;
    font-size: 16px;
    color: $--color-text-secondary;
  }
}
</style>

<style lang="scss" scoped>
@media screen and (max-width: 500px) {
  .practice-question {
    padding: 20px;
  }
}
</style>
