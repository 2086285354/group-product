import request from "@/utils/request";
//查询种植计划
export function getPlanInfo(query){
  return request({
    url: '/trakapi/trak/getPlanInfoList',
    method: 'get',
    params: query
  })
}
//查询农作物
export function getCropInfo(){
  return request({
    url: '/trakapi/trak/getCropInfoList',
    method: 'get',
  })
}
//添加
export function addPlanCropInfo(obj){
  return request({
    url: '/trakapi/trak/addPlanCropInfo',
    method: 'post',
    data: obj
  })
}
//修改种植记录跟踪
export function updatePlanCropInfo(obj){
  return request({
    url: 'trakapi/trak/updatePlanCropInfo',
    method: "put",
    data: obj
  })
}

//删除
export function deletePlanCropInfo(ids){
  return request({
    url: 'trakapi/trak/deletePlanCropInfo',
    method: "delete",
    data: ids
  })
}
