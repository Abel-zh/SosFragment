/*
 * Copyright (C) 2013  ethanchiu@126.com
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

package simple.framework.ioc.annotation.view;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @Description: 主键配置,不配置的时候默认找类的id或_id字段作为主键，column不配置的是默认为字段名，如果不设置主键，自动生成ID
 * @author: ethanchiu@126.com
 * @date: 2013-6-14
 */
@Target(
{ METHOD, FIELD })
@Retention(RUNTIME)
public @interface InjectField
{
	/**
	 * 设置配置的名
	 * 
	 * @return
	 */
	public String name() default "";

	/**
	 * 设置配置的默认值
	 * 
	 * @return
	 */
	public String defaultValue() default "";

}