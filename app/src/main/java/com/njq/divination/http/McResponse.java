/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.njq.divination.http;

import java.io.Serializable;

/**
 * 描述：网络请求基础bean
 * 作者：钮家齐
 * 时间: 2018-06-02 15:51
 */
public class McResponse<T> implements Serializable {

    private static final long serialVersionUID = 5213230387175987834L;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int status;
    public String message;
    public T data;

    @Override
    public String toString() {
        return "McResponse{\n" +//
               "\tcode=" + status + "\n" +//
               "\tmsg='" + message + "\'\n" +//
               "\tdata=" + data + "\n" +//
               '}';
    }
}
