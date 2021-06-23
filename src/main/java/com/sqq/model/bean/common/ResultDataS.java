package com.sqq.model.bean.common;

import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;

/**
 * @description: 带分页的返回值对象
 * @author: zhouyi
 * @create: 2019/3/23
 */
@lombok.Data
@ApiModel("ResultDatas")
public class ResultDataS<T> extends ResultData<Data<T>> {

  public ResultDataS(@NotNull T data, Pager pager, Integer code, String msg) {
    this.data = new Data<T>(data, pager);
    this.code = code;
    this.msg = msg;
  }

  public ResultDataS(@NotNull T data, Pager pager, Msg msg) {
    this(data, pager, msg.getCode(), msg.getMsg());
  }

  public ResultDataS(@NotNull T data, Pager pager) {
    this(data, pager, Msg.OK.getCode(), Msg.OK.getMsg());
  }
}
