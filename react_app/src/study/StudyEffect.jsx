import { useEffect, useState } from "react";

function StudyEffext() {

    const [count, setCount] = useState(0)

    function increase(){
        setCount(count+1);
    }

    // 렌더링 할 때마다 실행 (Mount, status가 변경될 때)
    useEffect(()=>{
        console.log("EFFECT1")
    })

    // Mount(첫 렌더링)될 때만 실행, 그 외 나머지는 안 함
    // 의존성 배열 
    useEffect(()=>{
        console.log("Effect2")
    }, [])


    // Mount할 때, count(특정한) status가 변경
    useEffect(()=>{
        console.log("Effect2")
    }, [count])

    useEffect(()=> {

        // clean up 코드(청소하는 코드/ Component가 소멸(Unmount)될 때 실행하고 싶은 코드)
        return()=>{

        }
    })

    return (
        <>
            <h1>Use Effect</h1>
            <h1>{count}</h1>
            <button onClick={increase}>NEXT</button>
        </>
    )
}                                                                                                                                                                                  ()=>{

}

export default StudyEffext;
