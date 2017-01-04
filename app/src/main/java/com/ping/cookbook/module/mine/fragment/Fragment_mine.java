package com.ping.cookbook.module.mine.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ping.cookbook.R;
import com.ping.cookbook.base.BaseFragment;
import com.ping.cookbook.bean.User;
import com.ping.cookbook.dao.UserDao;
import com.ping.cookbook.dbHelper.DBHelper;
import com.ping.cookbook.module.mine.activity.AboutActivity;
import com.ping.cookbook.module.mine.activity.CollectActivity;
import com.ping.cookbook.module.mine.activity.FeedBackActivity;
import com.ping.cookbook.utils.GlideCatchUtil;
import com.ping.cookbook.widgets.CommonDialog;
import com.ping.cookbook.widgets.EditMsgDialog;
import com.ping.cookbook.widgets.PhotoDialog;
import com.qq.e.ads.appwall.APPWall;

import java.io.File;
import java.util.List;

import ping.Lib.Utils.GetPath;
import ping.Lib.Utils.SDCardUtil;
import ping.Lib.Utils.ToastUtil;
import ping.Lib.widgets.RoundedImageView;


public class Fragment_mine extends BaseFragment implements View.OnClickListener, PhotoDialog.PhotoDialogListener, EditMsgDialog.EditMsgDialogListener {
    private View contentView;
    private List<User> userList;
    private RoundedImageView iv_image;
    private LinearLayout ll_clear;
    private CommonDialog commonDialog;
    private PhotoDialog photoDialog;
    private EditMsgDialog editMsgDialog;
    private static final int CHANGE_TITLE_WHAT = 1;
    private static final int MAX_SUFFIX_NUMBER = 3;
    private static final int CHNAGE_TITLE_DELAYMILLIS = 300;
    private static final char SUFFIX = '.';
    private TextView progress_text,tv_nickname,tv_sign;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateViewProxy(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_mine, container, false);
            initViews(contentView);
        }
        return contentView;
    }

    private void initViews(View view) {
        setToolbarVisible(View.GONE);
        iv_image = (RoundedImageView) view.findViewById(R.id.iv_image);
        ll_clear = (LinearLayout) view.findViewById(R.id.ll_clear);
        progress_text = (TextView) view.findViewById(R.id.progress_text);
        tv_nickname = (TextView) view.findViewById(R.id.tv_nickname);
        tv_sign = (TextView) view.findViewById(R.id.tv_sign);
        userList = DBHelper.getDaoSession().getUserDao().queryBuilder().where(UserDao.Properties.Id.eq(1)).list();
        if (userList.size() > 0) {
            String nickname = userList.get(0).getNickname();
            String sign = userList.get(0).getSign();
            Glide.with(this).load(userList.get(0).getUsericon()).asBitmap().placeholder(R.drawable.ic_launcher).into(iv_image);
            tv_nickname.setText(nickname==null||nickname.equals("")?"快手菜.游客":nickname);
            tv_sign.setText(sign==null||sign.equals("")?"爱，就是在一起，吃好多好多顿饭!":sign);
        }
        tv_sign.setOnClickListener(this);
        iv_image.setOnClickListener(this);
        tv_nickname.setOnClickListener(this);
        view.findViewById(R.id.tv_mycollect).setOnClickListener(this);
        view.findViewById(R.id.tv_myfb).setOnClickListener(this);
        view.findViewById(R.id.tv_myapp).setOnClickListener(this);
        view.findViewById(R.id.tv_myclear).setOnClickListener(this);
        view.findViewById(R.id.tv_myabout).setOnClickListener(this);
        view.findViewById(R.id.tv_myexit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_image:
                imagedialog();
                break;
            case R.id.tv_sign:
            case R.id.tv_nickname:
                editdialog();
                break;
            case R.id.tv_mycollect:
                startActivity(new Intent(getActivity(), CollectActivity.class));
                break;
            case R.id.tv_myfb:
                startActivity(new Intent(getActivity(), FeedBackActivity.class));
                break;
            case R.id.tv_myapp:
                APPWall wall = new APPWall(getActivity(), "1105752382", "2060712527577146");
                wall.setScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                wall.doShowAppWall();
                break;
            case R.id.tv_myclear:
                handler.sendEmptyMessage(CHANGE_TITLE_WHAT);
                ll_clear.setVisibility(View.VISIBLE);
                GlideCatchUtil.getInstance().clearCacheDiskSelf();
                GlideCatchUtil.getInstance().cleanCatchDisk();
                GlideCatchUtil.getInstance().clearCacheMemory();
                ll_clear.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ll_clear.setVisibility(View.GONE);
                        ToastUtil.showShort("清理完成");
                    }
                }, 1500);
                break;
            case R.id.tv_myabout:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            case R.id.tv_myexit:
                dialog();
                break;
        }
    }

    private void dialog() {
        if (commonDialog == null) {
            commonDialog = new CommonDialog(getActivity());
        }
        commonDialog.show();
    }

    private void editdialog() {
        userList = DBHelper.getDaoSession().getUserDao().queryBuilder().where(UserDao.Properties.Id.eq(1)).list();
        if (editMsgDialog == null) {
            editMsgDialog = new EditMsgDialog(getActivity());
            editMsgDialog.setEditMsgDialogListener(this);
        }
        editMsgDialog.show();
    }

    private void imagedialog() {
        userList = DBHelper.getDaoSession().getUserDao().queryBuilder().where(UserDao.Properties.Id.eq(1)).list();
        if (photoDialog == null) {
            photoDialog = new PhotoDialog(getActivity());
            photoDialog.setPhotoDialogListener(this);
        }
        photoDialog.show();
    }

    private Handler handler = new Handler() {
        private int num = 0;

        public void handleMessage(android.os.Message msg) {
            if (msg.what == CHANGE_TITLE_WHAT) {
                StringBuilder builder = new StringBuilder();
                if (num >= MAX_SUFFIX_NUMBER) {
                    num = 0;
                }
                num++;
                for (int i = 0; i < num; i++) {
                    builder.append(SUFFIX);
                }
                progress_text.setText("清理中" + builder.toString());
                if (ll_clear.getVisibility() == View.VISIBLE) {
                    handler.sendEmptyMessageDelayed(CHANGE_TITLE_WHAT, CHNAGE_TITLE_DELAYMILLIS);
                } else {
                    num = 0;
                }
            }
        }
    };

    @Override
    public void OnEditListener(String nickname, String sign) {
        hideInput();
        User user = null;
        if (userList.size() > 0) {
            user = userList.get(0);
        } else {
            user = new User();
            user.setId(Long.valueOf(1));
        }
        tv_nickname.setText(nickname==null||nickname.equals("")?"快手菜.游客":nickname);
        tv_sign.setText(sign==null||sign.equals("")?"爱，就是在一起，吃好多好多顿饭!":sign);
        user.setNickname(nickname);
        user.setSign(sign);
        DBHelper.getDaoSession().getUserDao().insertOrReplace(user);
    }

    @Override
    public void OnImageListener(int position) {
        if (position == 1) {
            if (mIsKitKat) {
                selectImageUriAfterKikat();
            } else {
                cropImageUri();
            }
        } else if (position == 2) {
            Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_NAME));
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 指定照片保存路径（SD卡），image为一个临时文件，每次拍照后这个图片都会被替换
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CODE_TACK_PHOTO);
        }
    }

    // 修改头像
    public static final int REQUEST_CODE_TACK_PHOTO = 10; // 只是一个requestcode而已,值无任何意义
    public static final int REQUEST_CODE_OPEN_ALBUM = 11;
    public static final int REQUEST_CODE_OPEN_ALBUM4 = 12;// 4.4版本以后
    public static final int REQUEST_CODE_OPEN_JALBUM4 = 13;// 4.4版本以后截图
    public String PHOTO_NAME = "icon.jpg"; // 拍得的全照
    public String PHOTO_NAME_CROP = ".jpg"; // 切割后的截图
    final boolean mIsKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;// 版本比较：是否是4.4及以上版本

    /**
     * <br>
     * 功能简述:4.4以上裁剪图片方法实现---------------------- 相册 <br>
     * 功能详细描述: <br>
     * 注意:
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void selectImageUriAfterKikat() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_OPEN_ALBUM4);
    }

    private void cropImageUri() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, REQUEST_CODE_OPEN_ALBUM);
    }

    /**
     * <br>
     * 功能简述: 4.4及以上改动版裁剪图片方法实现 --------------------相机 <br>
     * 功能详细描述: <br>
     * 注意:
     */
    private void cropImageUriAfterKikat(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/jpeg");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        PHOTO_NAME_CROP = System.currentTimeMillis() + PHOTO_NAME_CROP;
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(SDCardUtil.getExternalCacheDir(getActivity()), PHOTO_NAME_CROP)));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, REQUEST_CODE_OPEN_JALBUM4);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        File file = null;
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }

        switch (requestCode) {
            case REQUEST_CODE_TACK_PHOTO:// 拍照返回码
                if (SDCardUtil.isSDCardExist()) {// SDCard 存在，读取拍得的照片并处理
                    try {
                        file = new File(Environment.getExternalStorageDirectory(), PHOTO_NAME);
                        Uri uri = Uri.fromFile(file);
                        Intent intent = new Intent("com.android.camera.action.CROP");
                        intent.setDataAndType(uri, "image/*");
                        intent.putExtra("crop", "true");
                        intent.putExtra("aspectX", 1);
                        intent.putExtra("aspectY", 1);
                        intent.putExtra("outputX", 150);
                        intent.putExtra("outputY", 150);
                        intent.putExtra("scale", true);
                        // 切割照片后存放地址
                        PHOTO_NAME_CROP = System.currentTimeMillis() + PHOTO_NAME_CROP;
                        file = new File(SDCardUtil.getExternalCacheDir(getActivity()), PHOTO_NAME_CROP);
                        uri = Uri.fromFile(file);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        intent.putExtra("return-data", false);
                        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                        intent.putExtra("noFaceDetection", true); // no face
                        // detection
                        startActivityForResult(intent, 101);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
                break;

            case REQUEST_CODE_OPEN_ALBUM:
                if (data != null) {// 4.4一下版本
                    saveLocalImgPath(data.getData().getPath());
                }
                break;
            case REQUEST_CODE_OPEN_ALBUM4:
                if (data != null) {// 4.4版本
                    String mAlbumPicturePath = GetPath.getPath(getActivity(), data.getData());
                    cropImageUriAfterKikat(Uri.fromFile(new File(mAlbumPicturePath)));
                }
                break;
            case REQUEST_CODE_OPEN_JALBUM4:// 4.4版本
                saveLocalImgPath(Uri.fromFile(new File(SDCardUtil.getExternalCacheDir(getActivity()), PHOTO_NAME_CROP)).getPath());
                break;

            case 101:
                file = new File(SDCardUtil.getExternalCacheDir(getActivity()), PHOTO_NAME_CROP);
                Uri uri = Uri.fromFile(file);
                saveLocalImgPath(uri.getPath());
                break;
        }
    }

    public void saveLocalImgPath(final String path) {
        User user = null;
        if (userList.size() > 0) {
            user = userList.get(0);
        } else {
            user = new User();
            user.setId(Long.valueOf(1));
        }
        user.setUsericon("file://" + path);
        DBHelper.getDaoSession().getUserDao().insertOrReplace(user);
        Glide.with(this).load(user.getUsericon()).asBitmap().placeholder(R.drawable.ic_launcher).into(iv_image);
    }
}
