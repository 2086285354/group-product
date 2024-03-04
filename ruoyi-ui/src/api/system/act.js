import request from "@/utils/request";

export function getRecoverData(){
  return request({
    url:"/reco/recover/list",
    method:"GET"
  })
}

export function completeRecover(obj){
  return request({
    url:"/reco/recover/completeRecover",
    method:"post",
    data : obj
  })
}

export function getRecoverHistoryList(){
  return request({
    url:"/reco/recover/getRecoverHistory",
    method:"GET"
  })
}
