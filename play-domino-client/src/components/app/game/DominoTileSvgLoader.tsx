import React, {Suspense, lazy, SVGAttributes} from 'react';
// Glob import returns functions: () => Promise<{ default: ReactComponent }>
const DominoTileSvgModules = import.meta.glob<{
    default: React.FC<React.SVGProps<SVGSVGElement>>;
}>('/src/assets/tiles/*.svg', {query: '?react'});

type DominoTileSvgMap = Record<string, React.LazyExoticComponent<React.FC<React.SVGProps<SVGSVGElement>>>>;

const DominoTileSvgs: DominoTileSvgMap = {};

for (const path in DominoTileSvgModules) {
    const fileName = path.split('/').pop()?.replace('.svg', '').replace('?react', '') as string;
    const importer = DominoTileSvgModules[path];
    DominoTileSvgs[fileName] = lazy(importer);
}

interface DominoTileSvgProps extends SVGAttributes<HTMLOrSVGElement>{
    name: string;
    className?: string;
}

export const DominoTileSvg: React.FC<DominoTileSvgProps> = ({name, className, ...props}) => {
    const Component = DominoTileSvgs[name];
    if (!Component) {
        return <div className="text-red-500">DominoTileSvg not found: {name}</div>;
    }

    return (
        <Suspense fallback={<div className={`w-12 h-12 bg-gray-200 animate-pulse ${className}`}/>}>
            <Component className={className} {...props} />
        </Suspense>
    );
};
