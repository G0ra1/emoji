# !/bin/sh
rm -rf incloud-base-dict-4.0.0.tgz
rm -rf incloud-base-file-4.0.0.tgz
rm -rf incloud-base-mdm-4.0.0.tgz
rm -rf incloud-base-model-4.0.0.tgz
rm -rf incloud-base-msg-4.0.0.tgz
rm -rf incloud-base-oauth-4.0.0.tgz
rm -rf incloud-base-portal-4.0.0.tgz
rm -rf incloud-base-socket-4.0.0.tgz
rm -rf incloud-base-wf-4.0.0.tgz
rm -rf incloud-base-zuul-4.0.0.tgz
rm -rf incloud-biz-asset-4.0.0.tgz
rm -rf incloud-biz-mdm-4.0.0.tgz
rm -rf incloud-biz-study-4.0.0.tgz

helm package incloud-base-dict
helm package incloud-base-file
helm package incloud-base-mdm
helm package incloud-base-model
helm package incloud-base-msg
helm package incloud-base-oauth
helm package incloud-base-portal
helm package incloud-base-socket
helm package incloud-base-wf
helm package incloud-base-zuul
helm package incloud-biz-asset
helm package incloud-biz-mdm
helm package incloud-biz-study