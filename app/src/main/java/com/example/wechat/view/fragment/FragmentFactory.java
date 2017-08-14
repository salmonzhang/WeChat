package com.example.wechat.view.fragment;

/**
 * author:salmonzhang
 * Description:Fragment工厂
 * Date:2017/8/14 0014 11:00
 */

public class FragmentFactory {

    private static ContactFragment sContactFragment;
    private static ConversationFragment sConversationFragment;
    private static DynamicFragment sDynamicFragment;

    public static BaseFragment getFragment(int position) {
        switch (position) {
            case 0:
                if (sConversationFragment == null) {
                    sConversationFragment = new ConversationFragment();
                }
                return sConversationFragment;
            case 1:
                if (sContactFragment == null) {
                    sContactFragment = new ContactFragment();
                }
                return sContactFragment;
            case 2:
                if (sDynamicFragment == null) {
                    sDynamicFragment = new DynamicFragment();
                }
                return sDynamicFragment;
        }
        return null;
    }
}
