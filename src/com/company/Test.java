package com.company;

/**
 * ��ʱ��һ����Դ��ܿ���ѧ���ܶ����õĶ������ڿ�Simple Log��ʱ������һ��д��
 * �ڶ�boolean����ʱ����ʹ��|=����&=����^=�������㡣
 * �߼�������
 * �߼��������Ĳ�������ͽ����Ϊboolean�ͣ���������
 * �����߼��ǣ� && ���߼��룩 ||���߼���
 * ^���߼����� & ���߼��룩 | ���߼���
 * ��λ��'&'Ҳ����Ϊ�߼���ʹ�ã���δ���Ż�����'&&'�������Ǿ����Ż��ġ���'|'������Ҳ���ơ�
 * @author Administrator
 *
 */
public class Test {

    public static void main(String[] args) {
        boolean isTrue = false;
        isTrue |= true; // ��ͬ�� ��isTrue = isTrue | true;
        System.out.println(isTrue);

        isTrue = false;
        isTrue |= false;
        System.out.println(isTrue);

        isTrue = true;
        isTrue |= true;
        System.out.println(isTrue);

        isTrue = true;
        isTrue |= false;
        System.out.println(isTrue);
        isTrue &= false;
        System.out.println(isTrue);
        isTrue = true;
        isTrue ^= isTrue;
        System.out.println(isTrue);
    }
}

