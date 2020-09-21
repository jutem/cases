package com.jutem.cases.leetcode;

/**
 * @author dawei.chen02@ele.me
 * @create 2020-09-17
 */
public class MedianOfTwoSortedArrays {

    public static void main(String[] args) {
        System.out.printf(findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4}) + "");
    }

    //另一种思路:寻找中位数，可以转换成寻找第k小的数字，二分法效率更好

    //归并的思路,更优雅的写法
    public static double findMedianSortedArraysBetter(int[] A, int[] B) {
        int m = A.length;
        int n = B.length;
        int len = m + n;
        int left = -1, right = -1;
        int aStart = 0, bStart = 0;
        for (int i = 0; i <= len / 2; i++) {
            left = right;
            //大前提可以保证数组A取不到的情况下，数组B一定能取到
            if (aStart < m && (bStart >= n || A[aStart] < B[bStart])) {
                right = A[aStart++];
            } else {
                right = B[bStart++];
            }
        }
        if ((len & 1) == 0)
            return (left + right) / 2.0;
        else
            return right;
    }

    //归并的思路
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int allLength = nums1.length + nums2.length;

        //1.2.3.4 1,2 4/2 = 2 2 - 1
        //1.2.3 1 3/2 = 1

        boolean even = allLength % 2 == 0;
        int middle = 0;
        if (even) {
            middle = allLength / 2 - 1;
        } else {
            middle = allLength / 2;
        }

        int index = 0;
        int now = 0;
        boolean ready = false;
        double[] array = new double[2];
        for (int i = 0, j = 0; ; ) {

            if (i >= nums1.length) {
                now = nums2[j];
                ready = true;
                j++;
            }

            if (!ready && j >= nums2.length) {
                now = nums1[i];
                ready = true;
                i++;
            }

            if (!ready) {
                if (nums1[i] < nums2[j]) {
                    now = nums1[i];
                    i++;
                } else {
                    now = nums2[j];
                    j++;
                }
                ready = true;
            }

            if (even) {
                if (index == middle) {
                    array[0] = now;
                }
                if (index == middle + 1) {
                    array[1] = now;
                    break;
                }
            } else {
                if (index == middle) {
                    array[0] = now;
                    array[1] = now;
                    break;
                }
            }

            ready = false;
            index++;
        }

        return (array[0] + array[1]) / 2;
    }


}
