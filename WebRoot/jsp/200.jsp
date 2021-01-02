<%Object o = request.getAttribute("content");
if(o==null||o.equals("")){
	out.println("");
}else{
	response.setCharacterEncoding("GBK");
    out.print(o);
}
%>