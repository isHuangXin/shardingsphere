/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.sharding.route.engine.type.standard;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

class SQLRouteTest extends AbstractSQLRouteTest {
    
    @Test
    void assertNoTableUnicastRandomDataSource() {
        String sql = "SELECT id,name ";
        assertRoute(sql, Collections.singletonList(1));
    }
    
    @Test
    void assertWithBroadcastTable() {
        String sql = "SELECT id,name from t_order_item a join t_product b on a.product_id = b.product_id where user_id = ?";
        assertRoute(sql, Collections.singletonList(1));
    }
    
    @Test
    void assertAllBindingWithBroadcastTable() {
        String sql = "SELECT id,name from t_order a join t_order_item b on a.order_id = b.order_id join t_product c on b.product_id = c.product_id where a.user_id = ?";
        assertRoute(sql, Collections.singletonList(1));
    }
    
    @Test
    void assertComplexTableWithBroadcastTable() {
        String sql = "SELECT id,name from t_order a join t_user b on a.user_id = b.user_id join t_product c on a.product_id = c.product_id where a.user_id = ? and b.user_id =?";
        assertRoute(sql, Arrays.asList(1, 1));
    }
    
    @Test
    void assertInsertTable() {
        String sql = "INSERT INTO t_order (order_id, user_id) VALUES (?, ?)";
        assertRoute(sql, Arrays.asList(1, 1));
    }
}
