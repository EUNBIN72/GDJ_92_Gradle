function MyList() {
        const name = "winter";

        const products = [
            {title : 'Cabbage', id : 1},
            {title : 'Garlic', id : 2},
            {title : 'Apple', id : 3}
        ];

        const list = products.map(m=>{
            return (

            
            <li key={m.id}>
                {m.title}
            </li>
            )
        });
    
        fetch("http://localhost/notice/1")
            .then(r => r.json())
            .then(r => console.log(r));

  return (
    <>
        <h1>List Page</h1>
        <h1>{name}</h1>
        <ul>
            {list}
        </ul>
    </>
  )
}

export default MyList;
