package com.example.activitytest;

import java.util.Scanner;

public class demo {

        /**
          * 输入水果销售量
          * */
        public static int[] input_sales_volume(String[] fruit_name) {
            int[] sales = {0, 0, 0, 0, 0, 0, 0, 0, 0};
            System.out.println("请分别输入梨 、苹果、荔枝、葡萄、西瓜、橙子、龙眼、火龙果、车厘子的销售量(单位：斤) ：\n");
            Scanner scanner = new Scanner(System.in);
            for (int i=0; i < fruit_name.length; i++) {
                System.out.println(fruit_name[i]+"(斤):\t");
                sales[i] = Integer.parseInt(scanner.next());
            }
            return sales;
        }

        /**
         * 计算每一种水果的利润
         * */
         public static float[] calculation_profit(int[] sales, float[] profit, float[] original_price, float[] selling_price) {
            for (int i = 0; i < sales.length; i++) {
                profit[i] = (selling_price[i] - original_price[i])*sales[i];
            }
            return profit;
        }

        /**
         * 获取最大利润
         * */
         public static int get_max_profit(float[] profit) {
             int fruit_max_profit_id = 0;
            float max = 0.0f;
            float count;
            for (int i = 0; i < profit.length; i++) {
                count = profit[i];
                if ( max <= count) {
                    max = count;
                    fruit_max_profit_id = i;
                }
            }
            return fruit_max_profit_id;
        }
        /**
         * 计算总利润
         * */
         public static float calculation_total_profit(float[] profit) {
             float profit_all = 0.0f;
            for (int i = 0; i < profit.length; i++) {
                profit_all += profit[i];
            }
            return profit_all;
        }


    public static void main(String[] arg) {
        // 保存水果名称
        String[] fruit_name = {"梨", "苹果", "葡萄", "西瓜", "橙子", "龙眼", "火龙果", "车厘子"};
        // 定义水果进价 梨 、苹果、荔枝、葡萄、西瓜、橙子、龙眼、火龙果、车厘子
        float[] original_price = {3.1f, 2.3f, 5.8f, 4.9f, 0.8f, 3.6f, 8.6f, 9.0f, 17.2f};
        // 定义水果售价
        float[] selling_price = {4.2f, 3.5f, 7.2f, 6.7f, 1.5f, 4.3f, 10.0f, 12.0f, 20.5f};
        // 定义并初始化销量
        int[] sales = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        // 保存每种水果的利润
        float[] profit = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        // 最大利润水果编号
        int fruit_max_profit_id = 0;
        // 保存总利润
        float profit_all = 0;
        // 获取输入的销售额
        sales = input_sales_volume(fruit_name);
        // 获取每种水果的利润
        profit = calculation_profit(sales, profit, original_price, selling_price);
        // 获取最大利润水果的标识
        fruit_max_profit_id = get_max_profit(profit);
        // 计算总利润
        profit_all = calculation_total_profit(profit);
        // 打印相关信息
        System.out.println("今日总利润为 ："+ profit_all);
        System.out.println("今日利润最大的水果为： ："+ fruit_name[fruit_max_profit_id]);
        System.out.println("它的利润为 ："+ profit[fruit_max_profit_id]);

    }
}
