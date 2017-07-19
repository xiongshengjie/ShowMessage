package cn.xiong.showmessage.entity;

/**
 * Created by Administrator on 2017/7/18.
 */

public class General {

    private String nickname;
    private String headImgUrl;
    private String spaceName;
    private String behavior;
    private String behaviorRelationName;
    private String behaviorTime;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public String getBehaviorRelationName() {
        return behaviorRelationName;
    }

    public void setBehaviorRelationName(String behaviorRelationName) {
        this.behaviorRelationName = behaviorRelationName;
    }

    public String getBehaviorTime() {
        return behaviorTime;
    }

    public void setBehaviorTime(String behaviorTime) {
        this.behaviorTime = behaviorTime;
    }
}
