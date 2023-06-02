
//初始化时间
/****
 * oldDate :一个表示日期的字符串
 *  full :用于指定返回日期的格式
 */
const initDate = (oldDate,full) => {
    var odate = new Date(oldDate);
    var year =  odate.getFullYear();
    var month = odate.getMonth()<9? '0' + (odate.getMonth()+1) : odate.getMonth()+1;
    var date = odate.getDate()<10? '0'+odate.getDate() : odate.getDate();
    // 当 full 参数为 'all' 时，函数会将日期格式化为 '年月日' 的形式，例如 '2023年01月30日'
    if(full=='all'){
        var t = oldDate.split(" ")[0];
        // console.log(oldDate,t.split('-')[0],t.split('-')[1],t.split('-')[2]);
        return t.split('-')[0]+'年'+t.split('-')[1]+'月'+t.split('-')[2]+'日';
    }else if(full=='year'){
        return year
    }else if(full== 'month'){
        return odate.getMonth()+1
    }else if(full == 'date'){
        return date
    }else if(full== 'newDate'){
        return year+'年'+month+'月'+date+'日';
    }
}



export {
        initDate//设置时间

    }
