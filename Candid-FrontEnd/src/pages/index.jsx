import { useRef } from "react"

function Index(){

    const text = useRef(null)

    function enter(){

        const textValue = text.current.value;

        fetch("/api/test/run",{

            method:"POST",
            headers:{

                "Content-Type":"application/json"

            },
            body:JSON.stringify({

                text: textValue

            })

        }).then(res => res.text()).then(data => {

            alert("java says " + data);

        })
        
    }

    return(

        <div className="
        
            flex
            flex-col
            items-center  
            
        ">
            <h1 className="
            
                mt-10
            
            ">Hello world this is page 1</h1>
            <div className="
                
                mt-10
                flex
                justify-center
                gap-10
                w-4/5
            
            ">
                <input placeholder="text" type="text" ref={text} className="
            
                    outline-1

                    hover:text-blue-700

                "></input>
                <button onClick={enter}>enter</button>
            </div>
        </div>

    )
    
}

export default Index